import csv
import string
from collections import defaultdict


def to_lowercase(text):
    return text.lower()


def tokenize(text):
    translator = str.maketrans('', '', string.punctuation)
    return text.translate(translator).split()


def load_dataset(filename):
    dataset = []
    categories = set()

    with open(filename, newline='', encoding='utf-8') as file:
        reader = csv.reader(file)
        for row in reader:
            if len(row) != 2:
                continue
            text, category = row
            text = to_lowercase(text)
            dataset.append((text, category))
            categories.add(category)

    return dataset, list(categories)


def classify_text(input_text, dataset, categories):
    category_word_count = defaultdict(int)
    total_word_count = defaultdict(int)
    total_words = 0

    input_text = to_lowercase(input_text)
    words = tokenize(input_text)

    for text, category in dataset:
        text_words = tokenize(text)
        total_words += len(text_words)

        for word in words:
            if word in text_words:
                category_word_count[category] += 1

        total_word_count[category] += len(text_words)

    max_prob = 0
    best_category = None

    for category in categories:
        prob = (category_word_count[category] + 1) / (total_word_count[category] + len(categories))
        if prob > max_prob:
            max_prob = prob
            best_category = category

    print(f"Predicted Category: {best_category}")


if __name__ == "__main__":
    dataset, categories = load_dataset("20newsgroups_dataset.csv")

    input_text = input("Enter text to classify: ")
    classify_text(input_text, dataset, categories)
