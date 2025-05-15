import os
import numpy as np
import re
import string
from collections import defaultdict, Counter
from sklearn.model_selection import train_test_split


# Function to load dataset from extracted directory
def load_uci_20newsgroups_dataset(dataset_path):
    categories = os.listdir(dataset_path)
    categories = [cat for cat in categories if
                  os.path.isdir(os.path.join(dataset_path, cat))]  # Filter out only directories

    texts, labels = [], []
    category_map = {category: idx for idx, category in enumerate(categories)}  # Assign numeric labels

    for category in categories:
        category_path = os.path.join(dataset_path, category)
        for filename in os.listdir(category_path):
            file_path = os.path.join(category_path, filename)
            try:
                with open(file_path, 'r', encoding='latin-1') as f:
                    text = f.read()
                    texts.append(text)
                    labels.append(category_map[category])
            except Exception as e:
                print(f"Error reading {file_path}: {e}")

    return texts, np.array(labels), category_map


# Function to clean and tokenize text
def preprocess_text(text):
    text = text.lower()
    text = re.sub(f"[{string.punctuation}]", " ", text)  # Remove punctuation
    tokens = text.split()  # Tokenization
    return tokens


# Function to compute word frequency for each class
def compute_word_frequencies(X_train, y_train):
    class_word_counts = defaultdict(Counter)  # Word count for each class
    class_counts = Counter(y_train)  # Document count per class
    vocabulary = set()

    for text, label in zip(X_train, y_train):
        words = preprocess_text(text)
        vocabulary.update(words)
        class_word_counts[label].update(words)

    return class_word_counts, class_counts, vocabulary


# Function to compute log probabilities for Multinomial Naïve Bayes
def train_multinomial_nb(X_train, y_train):
    class_word_counts, class_counts, vocabulary = compute_word_frequencies(X_train, y_train)
    total_docs = len(y_train)

    # Compute log probabilities
    log_class_priors = {cls: np.log(count / total_docs) for cls, count in class_counts.items()}
    word_probs = {}

    for cls, word_count in class_word_counts.items():
        total_words = sum(word_count.values()) + len(vocabulary)  # Additive smoothing
        word_probs[cls] = {word: np.log((word_count[word] + 1) / total_words) for word in vocabulary}

    return log_class_priors, word_probs, vocabulary


# Function to classify text using Multinomial Naïve Bayes
def predict_multinomial_nb(X_test, log_class_priors, word_probs, vocabulary):
    predictions = []

    for text in X_test:
        words = preprocess_text(text)
        class_scores = {cls: log_prior for cls, log_prior in log_class_priors.items()}

        for cls in class_scores:
            for word in words:
                if word in vocabulary:
                    class_scores[cls] += word_probs[cls].get(word, np.log(1 / len(vocabulary)))  # Handle unseen words

        predictions.append(max(class_scores, key=class_scores.get))

    return np.array(predictions)


# Set the path where the dataset is extracted
dataset_path = "./mini_newsgroups"

# Load the dataset
X, y, category_map = load_uci_20newsgroups_dataset(dataset_path)

# Train-Test Split
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# Train Naïve Bayes Classifier
log_class_priors, word_probs, vocabulary = train_multinomial_nb(X_train, y_train)

# Predict on Test Data
y_pred = predict_multinomial_nb(X_test, log_class_priors, word_probs, vocabulary)

# Compute Accuracy
accuracy = np.mean(y_pred == y_test)
print(f"Accuracy of Multinomial Naïve Bayes: {accuracy:.4f}")
