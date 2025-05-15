from itertools import combinations

# Function to generate candidate itemsets of size k
def generate_candidates(prev_frequent, k):
    candidates = set()
    prev_list = list(prev_frequent)

    for i in range(len(prev_list)):
        for j in range(i + 1, len(prev_list)):
            candidate = prev_list[i] | prev_list[j]
            if len(candidate) == k:
                candidates.add(candidate)

    return candidates

# Function to count occurrences of itemsets in transactions
def count_occurrences(transactions, candidates):
    counts = {itemset: 0 for itemset in candidates}

    for transaction in transactions:
        for candidate in candidates:
            if candidate.issubset(transaction):
                counts[candidate] += 1

    return counts


# Function to generate frequent itemsets using Apriori
def apriori(transactions, min_support):
    num_transactions = len(transactions)
    support_threshold = min_support * num_transactions
    itemsets = {frozenset([item]) for transaction in transactions for item in transaction}

    # Get frequent 1-itemsets
    counts = count_occurrences(transactions, itemsets)
    frequent_itemsets = {itemset: count for itemset, count in counts.items() if count >= support_threshold}

    result = frequent_itemsets.copy()
    k = 2

    while frequent_itemsets:
        candidates = generate_candidates(set(frequent_itemsets.keys()), k)
        counts = count_occurrences(transactions, candidates)
        frequent_itemsets = {itemset: count for itemset, count in counts.items() if count >= support_threshold}
        result.update(frequent_itemsets)
        k += 1

    return result, num_transactions


# Function to generate association rules
def generate_association_rules(frequent_itemsets, num_transactions, min_confidence):
    rules = []

    for itemset in frequent_itemsets:
        if len(itemset) > 1:
            for i in range(1, len(itemset)):
                for antecedent in combinations(itemset, i):
                    antecedent = frozenset(antecedent)
                    consequent = itemset - antecedent
                    support_itemset = frequent_itemsets[itemset] / num_transactions
                    support_antecedent = frequent_itemsets[antecedent] / num_transactions
                    confidence = support_itemset / support_antecedent

                    if confidence >= min_confidence:
                        rules.append((antecedent, consequent, support_itemset, confidence))

    return rules


# Function to get input transactions
def get_transactions():
    num_transactions = int(input("Enter the number of transactions: "))
    transactions = []

    for i in range(num_transactions):
        items = input(f"Enter items for transaction {i + 1} (comma-separated): ").split(',')
        transactions.append(set(item.strip() for item in items))

    return transactions


# Function to get minimum support and confidence
def get_parameters():
    min_support = float(input("Enter minimum support (0 to 1): "))
    min_confidence = float(input("Enter minimum confidence (0 to 1): "))
    return min_support, min_confidence


# Get input transactions
transactions = get_transactions()

# Get minimum support and confidence
min_support, min_confidence = get_parameters()

# Run Apriori Algorithm
frequent_itemsets, num_transactions = apriori(transactions, min_support)

# Generate Association Rules
rules = generate_association_rules(frequent_itemsets, num_transactions, min_confidence)

# Display Frequent Itemsets
print("\nFrequent Itemsets:")
for itemset, count in frequent_itemsets.items():
    print(f"{set(itemset)} - Support: {count / num_transactions:.2f}")

# Display Association Rules
print("\nAssociation Rules:")
for antecedent, consequent, support, confidence in rules:
    print(f"{set(antecedent)} → {set(consequent)} | Support: {support:.2f} | Confidence: {confidence:.2f}")
