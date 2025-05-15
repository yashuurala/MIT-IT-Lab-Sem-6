import pandas as pd
import numpy as np
import pprint

# === Entropy Function ===
def entropy(y):
    values, counts = np.unique(y, return_counts=True)
    probabilities = counts / counts.sum()
    return -np.sum(probabilities * np.log2(probabilities))

# === Information Gain Function ===
def information_gain(X, y, feature):
    total_entropy = entropy(y)
    values, counts = np.unique(X[feature], return_counts=True)

    weighted_entropy = 0
    subset_entropies = {}
    for val, count in zip(values, counts):
        subset_y = y[X[feature] == val]
        ent = entropy(subset_y)
        weighted_entropy += (count / sum(counts)) * ent
        subset_entropies[val] = ent

    gain = total_entropy - weighted_entropy
    return gain, total_entropy, subset_entropies

# === Find Best Feature ===
def best_split(X, y):
    best_feature = None
    max_gain = -1
    for feature in X.columns:
        gain, _, _ = information_gain(X, y, feature)
        if gain > max_gain:
            max_gain = gain
            best_feature = feature
    return best_feature

# === ID3 Recursive Tree Builder ===
def id3(X, y, features):
    if len(np.unique(y)) == 1:
        return np.unique(y)[0]
    if len(features) == 0:
        return y.value_counts().idxmax()

    best_feature = best_split(X, y)

    tree = {best_feature: {}}
    for value in np.unique(X[best_feature]):
        subset_X = X[X[best_feature] == value].drop(columns=[best_feature])
        subset_y = y[X[best_feature] == value]
        tree[best_feature][value] = id3(subset_X, subset_y, subset_X.columns)

    return tree

# === Predict for a Single Sample ===
def predict_from_tree(tree, sample):
    if not isinstance(tree, dict):
        return tree  # Leaf node

    feature = next(iter(tree))
    feature_value = sample.get(feature)

    if feature_value not in tree[feature]:
        return "Unknown"  # Handle unseen feature values

    subtree = tree[feature][feature_value]
    return predict_from_tree(subtree, sample)

# === Load CSV Dataset ===
df = pd.read_csv("play_ball.csv")  # Make sure this file is in your working directory

# Split into features and target
X = df.drop(columns=['PlayBall'])
y = df['PlayBall']



# === Build and Print Decision Tree ===
decision_tree = id3(X, y, X.columns)
print("\nGenerated Decision Tree:")
pprint.pprint(decision_tree)

# === Accept New Sample and Predict ===
print("\nEnter details of a new sample to classify:")
new_sample = {
    'Outlook': input("Enter Outlook (Sunny/Overcast/Rain): ").strip(),
    'Temperature': input("Enter Temperature (Hot/Mild/Cool): ").strip(),
    'Humidity': input("Enter Humidity (High/Normal): ").strip(),
    'Wind': input("Enter Wind (Weak/Strong): ").strip()
}

prediction = predict_from_tree(decision_tree, new_sample)
print(f"\nPrediction: Play Ball = {prediction}")
