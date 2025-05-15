import pandas as pd
import numpy as np

# === Gaussian Probability Function ===
def gaussian_probability(x, mean, variance):
    exponent = np.exp(-((x - mean) ** 2) / (2 * variance))
    return (1 / np.sqrt(2 * np.pi * variance)) * exponent

# === Compute Mean, Variance, Prior for Each Class ===
def compute_statistics(X, y):
    class_labels = np.unique(y)
    statistics = {}

    for label in class_labels:
        X_class = X[y == label]
        statistics[label] = {
            "mean": np.mean(X_class, axis=0),
            "variance": np.var(X_class, axis=0) + 1e-6,  # Prevent divide by zero
            "prior": len(X_class) / len(X)
        }

    return statistics

# === Predict Labels Using Gaussian Naive Bayes ===
def predict(X, statistics):
    predictions = []

    for sample in X:
        class_probabilities = {}

        for label, params in statistics.items():
            mean, variance, prior = params["mean"], params["variance"], params["prior"]
            likelihood = np.prod(gaussian_probability(sample, mean, variance))
            class_probabilities[label] = prior * likelihood

        predictions.append(max(class_probabilities, key=class_probabilities.get))

    return np.array(predictions)

# === Load Dataset from Local CSV ===
# Make sure the CSV file is in the same directory as this script
filename = "pima_diabetes.csv"

columns = ['Pregnancies', 'Glucose', 'BloodPressure', 'SkinThickness', 'Insulin', 'BMI',
           'DiabetesPedigreeFunction', 'Age', 'Outcome']

df = pd.read_csv(filename, names=columns)

# === Split Data into Features and Labels ===
X = df.drop(columns=['Outcome']).values
y = df['Outcome'].values

# === Split into Training and Testing Sets (80-20 split) ===
split_ratio = 0.8
split_index = int(split_ratio * len(X))

X_train, X_test = X[:split_index], X[split_index:]
y_train, y_test = y[:split_index], y[split_index:]

# === Train the Naive Bayes Classifier ===
statistics = compute_statistics(X_train, y_train)

# === Predict on Test Set ===
y_pred = predict(X_test, statistics)

# === Accuracy ===
accuracy = np.mean(y_pred == y_test)
print(f"Accuracy of Gaussian Naïve Bayes: {accuracy:.4f}")
