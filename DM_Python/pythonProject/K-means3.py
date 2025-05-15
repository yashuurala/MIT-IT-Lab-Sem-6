import numpy as np

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
        centroids = np.array([
            np.mean(X[cluster], axis=0) if len(cluster) > 0 else old_centroids[i]
            for i, cluster in enumerate(clusters)
        ])

        if np.all(old_centroids == centroids) and i > 200:
            print("Broke due to optimality at iter:", i, "The points are:", centroids)
            break

    return clusters, centroids

if __name__ == '__main__':
    import matplotlib.pyplot as plt

    # === Get user input ===
    n = int(input("Enter number of data points: "))
    X = []

    print("Enter each point as 'x y' (space-separated):")
    for _ in range(n):
        x, y = map(float, input().split())
        X.append([x, y])

    X = np.array(X)
    k = int(input("Enter number of clusters (k): "))

    # Run K-Means
    clusters, centroids = k_means(X, k=k)

    # === Plotting ===
    fig, ax = plt.subplots(figsize=(8, 6))

    for i, index in enumerate(clusters):
        points = X[index].T
        ax.scatter(*points, label=f'Cluster {i+1}')

    for point in centroids:
        ax.scatter(*point, marker="x", color='black', linewidth=2, s=100)

    ax.set_title("K-Means Clustering Result")
    ax.legend()
    plt.grid(True)
    plt.show()
