import numpy as np

# === Gaussian Probability Function ===
def gaussian_probability(x, mean, variance):
    exponent = np.exp(-((x - mean) ** 2) / (2 * variance))
    return (1 / np.sqrt(2 * np.pi * variance)) * exponent

# === Compute Mean, Variance, Prior per Class ===
def compute_statistics(X, y):
    class_labels = np.unique(y)
    statistics = {}

    for label in class_labels:
        X_class = X[y == label]
        statistics[label] = {
            "mean": np.mean(X_class, axis=0),
            "variance": np.var(X_class, axis=0) + 1e-6,  # Avoid zero division
            "prior": len(X_class) / len(X)
        }

    return statistics

# === Predict Labels ===
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

# === Manual Train and Test Data ===

# 3 training samples (8 features each, simplified for demo)
X_train = np.array([
    [6, 148, 72, 35, 0, 33.6, 0.627, 50],
    [1, 85, 66, 29, 0, 26.6, 0.351, 31],
    [8, 183, 64, 0, 0, 23.3, 0.672, 32]
])
y_train = np.array([1, 0, 1])

# 3 test samples
X_test = np.array([
    [1, 89, 66, 23, 94, 28.1, 0.167, 21],
    [0, 137, 40, 35, 168, 43.1, 2.288, 33],
    [5, 116, 74, 0, 0, 25.6, 0.201, 30]
])
y_test = np.array([0, 1, 1])  # Ground truth

# === Train Classifier ===
statistics = compute_statistics(X_train, y_train)

# === Predict on Test Set ===
y_pred = predict(X_test, statistics)

# === Output in Required Format ===
correct = 0
for i in range(len(y_test)):
    print(f"Test Sample {i+1}: Predicted = {y_pred[i]}, Actual = {y_test[i]}")
    if y_pred[i] == y_test[i]:
        correct += 1

accuracy = (correct / len(y_test)) * 100
print(f"Accuracy: {accuracy:.2f}%")
