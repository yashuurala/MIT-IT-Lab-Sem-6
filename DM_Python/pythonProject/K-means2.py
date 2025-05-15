import numpy as np
import matplotlib.pyplot as plt


def euclidean_distance(x1, x2):
    return np.sqrt(np.sum((x1 - x2) ** 2))


def k_means(X, k, max_iters=250):
    num_samples, num_features = X.shape
    indices = np.random.choice(num_samples, k, replace=False)
    centroids = X[indices]

    clusters = [[] for _ in range(k)]

    for i in range(max_iters):
        clusters = [[] for _ in range(k)]

        for idx, sample in enumerate(X):
            distances = [euclidean_distance(sample, centroid) for centroid in centroids]
            closest_cluster = np.argmin(distances)
            clusters[closest_cluster].append(idx)

        old_centroids = centroids.copy()
        centroids = np.array([np.mean(X[cluster], axis=0) if len(cluster) > 0 else old_centroids[i]
                              for i, cluster in enumerate(clusters)])

        if np.all(old_centroids == centroids) and i > 200:
            print("\nConverged at iteration:", i)
            break

    return clusters, centroids


if __name__ == '__main__':
    # Input section
    num_points = int(input("Enter number of points: "))
    X = []
    print("Enter each point as 'x y':")
    for _ in range(num_points):
        x, y = map(float, input().split())
        X.append([x, y])
    X = np.array(X)

    k = int(input("Enter number of clusters: "))

    # Run K-means
    clusters, centroids = k_means(X, k)

    # Output final assignment
    print("\nFinal Cluster Assignments:")
    for cluster_idx, point_indices in enumerate(clusters):
        for idx in point_indices:
            print(f"Point {X[idx]} => Cluster {cluster_idx}")

    # Plot the result
    fig, ax = plt.subplots(figsize=(8, 6))
    colors = ['blue', 'orange', 'green', 'purple', 'red', 'brown', 'pink', 'gray']

    for i, index in enumerate(clusters):
        points = X[index].T
        ax.scatter(*points, color=colors[i % len(colors)], label=f'Cluster {i}')

    for point in centroids:
        ax.scatter(*point, marker="x", color='black', linewidth=2)

    plt.title("K-Means Clustering Result")
    plt.xlabel("X")
    plt.ylabel("Y")
    plt.legend()
    plt.grid(True)
    plt.show()
