import numpy as np
import pandas as pd
from collections import defaultdict
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.model_selection import train_test_split
from sklearn.naive_bayes import MultinomialNB
from sklearn.preprocessing import LabelEncoder
from sklearn.metrics import accuracy_score

# Load dataset from CSV file
csv_file = "20newsgroups_dataset.csv"  # Change this to your actual file
df = pd.read_csv(csv_file)

# Ensure dataset is loaded correctly
print("Dataset Loaded Successfully!\n")
print(df.head())

# Extract text and category columns
X = df['Text']
y = df['Category']

# Encode category labels
label_encoder = LabelEncoder()
y_encoded = label_encoder.fit_transform(y)

# Split dataset into train and test sets
X_train, X_test, y_train, y_test = train_test_split(X, y_encoded, test_size=0.2, random_state=42)

# Convert text to TF-IDF features
vectorizer = TfidfVectorizer()
X_train_tfidf = vectorizer.fit_transform(X_train)
X_test_tfidf = vectorizer.transform(X_test)

# Train Naive Bayes classifier
classifier = MultinomialNB()
classifier.fit(X_train_tfidf, y_train)

# Predict on test set
y_pred = classifier.predict(X_test_tfidf)

# Calculate accuracy
accuracy = accuracy_score(y_test, y_pred)

# Function to compute confusion matrix
def confusion_matrix(y_true, y_pred, num_classes):
    matrix = np.zeros((num_classes, num_classes), dtype=int)
    for true, pred in zip(y_true, y_pred):
        matrix[true, pred] += 1
    return matrix

# Function to compute precision, recall
def precision_recall(conf_matrix):
    num_classes = conf_matrix.shape[0]
    precision = defaultdict(float)
    recall = defaultdict(float)

    for i in range(num_classes):
        tp = conf_matrix[i, i]
        fp = sum(conf_matrix[:, i]) - tp
        fn = sum(conf_matrix[i, :]) - tp
        precision[i] = tp / (tp + fp) if (tp + fp) > 0 else 0
        recall[i] = tp / (tp + fn) if (tp + fn) > 0 else 0

    return precision, recall

# Compute confusion matrix
num_classes = max(y_test.max(), y_pred.max()) + 1
conf_matrix = confusion_matrix(y_test, y_pred, num_classes)

# Compute precision & recall
precision, recall = precision_recall(conf_matrix)

# Display results
print("\nConfusion Matrix:")
print(conf_matrix)

print("\nPrecision per class:")
for cls, prec in precision.items():
    print(f"Class {cls} ({label_encoder.inverse_transform([cls])[0]}): {prec:.4f}")

print("\nRecall per class:")
for cls, rec in recall.items():
    print(f"Class {cls} ({label_encoder.inverse_transform([cls])[0]}): {rec:.4f}")

print(f"\nOverall Accuracy: {accuracy:.4f}")
