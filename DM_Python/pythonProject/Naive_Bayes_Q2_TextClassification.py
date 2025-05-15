import pandas as pd
import numpy as np
import re
import string
from collections import defaultdict, Counter
from sklearn.model_selection import train_test_split

# Load dataset from CSV
csv_file_path = "20newsgroups_dataset.csv"
df = pd.read_csv(csv_file_path)

# Check for missing values
df.dropna(subset=["Text", "Category"], inplace=True)

# Map categories to numeric labels
categories = sorted(df["Category"].unique())
category_map = {category: idx for idx, category in enumerate(categories)}
df["Label"] = df["Category"].map(category_map)

# Preprocess text
def preprocess_text(text):
    text = text.lower()
    text = re.sub(f"[{string.punctuation}]", " ", text)
    tokens = text.split()
    return tokens

# Compute word frequencies
def compute_word_frequencies(X_train, y_train):
    class_word_counts = defaultdict(Counter)
    class_counts = Counter(y_train)
    vocabulary = set()

    for text, label in zip(X_train, y_train):
        words = preprocess_text(text)
        vocabulary.update(words)
        class_word_counts[label].update(words)

    return class_word_counts, class_counts, vocabulary

# Train Naïve Bayes
def train_multinomial_nb(X_train, y_train):
    class_word_counts, class_counts, vocabulary = compute_word_frequencies(X_train, y_train)
    total_docs = len(y_train)
    log_class_priors = {cls: np.log(count / total_docs) for cls, count in class_counts.items()}
    word_probs = {}

    for cls, word_count in class_word_counts.items():
        total_words = sum(word_count.values()) + len(vocabulary)
        word_probs[cls] = {word: np.log((word_count[word] + 1) / total_words) for word in vocabulary}

    return log_class_priors, word_probs, vocabulary

# Predict using Naïve Bayes
def predict_multinomial_nb(X_test, log_class_priors, word_probs, vocabulary):
    predictions = []
    for text in X_test:
        words = preprocess_text(text)
        class_scores = {cls: log_prior for cls, log_prior in log_class_priors.items()}

        for cls in class_scores:
            for word in words:
                if word in vocabulary:
                    class_scores[cls] += word_probs[cls].get(word, np.log(1 / len(vocabulary)))

        predictions.append(max(class_scores, key=class_scores.get))
    return np.array(predictions)

# Train-Test Split
X_train, X_test, y_train, y_test = train_test_split(df["Text"], df["Label"], test_size=0.2, random_state=42)

# Check if X_test is empty before training
if len(X_test) == 0:
    raise ValueError("X_test is empty after splitting. Check dataset and preprocessing steps.")

# Train model
log_class_priors, word_probs, vocabulary = train_multinomial_nb(X_train, y_train)

# Predict and evaluate
y_pred = predict_multinomial_nb(X_test, log_class_priors, word_probs, vocabulary)
accuracy = np.mean(y_pred == y_test)
print(f"Accuracy: {accuracy:.4f}")

# Map labels back to categories
label_to_category = {v: k for k, v in category_map.items()}

# Sample predictions
num_samples = min(5, len(X_test))  # Prevent out-of-bounds error
for i in range(num_samples):
    print(f"Text: {X_test.values[i][:500]}...")  # Use .values instead of iloc
    print(f"Predicted: {label_to_category[y_pred[i]]}, Actual: {label_to_category[y_test.values[i]]}\n")
