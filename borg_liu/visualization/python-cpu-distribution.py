import pandas as pd
import matplotlib.pyplot as plt

# Read CSV file into a DataFrame
file_path = 'cpu-usage-ranking.csv'  # Replace with the actual path to your CSV file
df = pd.read_csv(file_path)

# Normalize avgcpu
max_avgcpu = df['avgcpu'].max()
df['normalized_avgcpu'] = df['avgcpu'] / max_avgcpu

# Create 10 groups
df['usage_group'] = pd.cut(df['normalized_avgcpu'], bins=10, labels=False)

# Plot the bar chart
plt.figure(figsize=(10, 6))  # Adjust the figure size if needed
counts = df['usage_group'].value_counts().sort_index(ascending=False)  # Reverse the order
interval_labels = [f'{i * 0.1:.1f}-{(i + 1) * 0.1:.1f}' for i in sorted(counts.index, reverse=True)]
counts.index = interval_labels
counts.plot(kind='bar', color='blue', edgecolor='black')
plt.title('Number of Instances in CPU Usage Groups')
plt.xlabel('Normalized CPU Usage Intervals')
plt.ylabel('Number of Instances')
plt.xticks(rotation=45, ha='right')
plt.grid(axis='y')
plt.show()

