import numpy as np

def euclidean_distance(x1, x2):
    return np.sqrt(np.sum((x1 - x2) ** 2))

def k_means(X, k, max_iters=250):
    num_samples, num_features = X.shape

    indices = np.random.choice(num_samples, k, replace=False)  # Initial random cluster centroid
    centroids = X[indices]  # Picking the centroids from the dataset

    clusters = [[] for _ in range(k)]  # Make arrays for each cluster

    for i in range(max_iters):
        clusters = [[] for _ in range(k)]

        # Assign samples to the closest cluster
        for idx, sample in enumerate(X):
            distances = [euclidean_distance(sample, centroid) for centroid in centroids]
            closest_cluster = np.argmin(distances)  # Returns index of min distance
            clusters[closest_cluster].append(idx)

        old_centroids = centroids.copy()
        centroids = np.array([np.mean(X[cluster], axis=0) if len(cluster) > 0 else old_centroids[i]
                              for i, cluster in enumerate(clusters)])

        # Check for convergence
        if np.all(old_centroids == centroids) and i > 200:
            print("Broke due to optimality at iter:", i, "The points are:", centroids)
            break

    return clusters, centroids

if __name__ == '__main__':
    from sklearn.datasets import make_blobs
    import matplotlib.pyplot as plt

    # Example dataset
    X = np.array([
        [0, 6], [1, 2], [2, 7], [3, 5], [2, 3], [2, 0],
        [9, 4], [9, 6], [3, 2], [8, 5], [7, 3], [8, 3]
    ])

    clusters, centroids = k_means(X, k=3)

    fig, ax = plt.subplots(figsize=(8, 6))

    for i, index in enumerate(clusters):
        points = X[index].T
        ax.scatter(*points)

    for point in centroids:
        ax.scatter(*point, marker="x", color='black', linewidth=2)

    plt.show()
