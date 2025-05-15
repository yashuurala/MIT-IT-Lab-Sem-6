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

# === Dataset (Play Ball) ===
data = {
    'Outlook': ['Sunny', 'Sunny', 'Overcast', 'Rain', 'Rain', 'Rain', 'Overcast',
                'Sunny', 'Sunny', 'Rain', 'Sunny', 'Overcast', 'Overcast', 'Rain'],
    'Temperature': ['Hot', 'Hot', 'Hot', 'Mild', 'Cool', 'Cool', 'Cool',
                    'Mild', 'Cool', 'Mild', 'Mild', 'Mild', 'Hot', 'Mild'],
    'Humidity': ['High', 'High', 'High', 'High', 'Normal', 'Normal', 'Normal',
                 'High', 'Normal', 'Normal', 'Normal', 'High', 'Normal', 'High'],
    'Wind': ['Weak', 'Strong', 'Weak', 'Weak', 'Weak', 'Strong', 'Strong',
             'Weak', 'Weak', 'Weak', 'Strong', 'Strong', 'Weak', 'Strong'],
    'PlayBall': ['No', 'No', 'Yes', 'Yes', 'Yes', 'No', 'Yes',
                 'No', 'Yes', 'Yes', 'Yes', 'Yes', 'Yes', 'No']
}

df = pd.DataFrame(data)
X = df.drop(columns=['PlayBall'])
y = df['PlayBall']

# === User Input for Attribute ===
attribute = input("Enter attribute to compute entropy & info gain (e.g., Wind, Outlook): ").strip()

gain, total_entropy, subset_entropies = information_gain(X, y, attribute)

# === Print Entropy and Gain ===
print(f"\nEntropy(S): {total_entropy:.4f}")
for val, ent in subset_entropies.items():
    print(f"Entropy(S_{val}): {ent:.4f}")
print(f"Information Gain(S, {attribute}): {gain:.4f}")

# === Build and Print Decision Tree ===
decision_tree = id3(X, y, X.columns)
print("\nGenerated Decision Tree:")
pprint.pprint(decision_tree)
