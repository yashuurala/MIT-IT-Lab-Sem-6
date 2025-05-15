import numpy as np
from collections import defaultdict
from Naive_Bayes_Q2_DS_Acc import y_test, y_pred, accuracy

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
num_classes = len(np.unique(y_test))
conf_matrix = confusion_matrix(y_test, y_pred, num_classes)

# Compute precision & recall
precision, recall = precision_recall(conf_matrix)

# Display results
print("\nConfusion Matrix:")
print(conf_matrix)

print("\nPrecision per class:")
for cls, prec in precision.items():
    print(f"Class {cls}: {prec:.4f}")

print("\nRecall per class:")
for cls, rec in recall.items():
    print(f"Class {cls}: {rec:.4f}")

print(f"\nOverall Accuracy: {accuracy:.4f}")
