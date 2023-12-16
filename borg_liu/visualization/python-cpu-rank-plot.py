import pandas as pd
import matplotlib.pyplot as plt

# Read CSV file into a DataFrame
file_path = 'cpu-usage-ranking.csv'  # Replace with the actual path to your CSV file
df = pd.read_csv(file_path)

# Extract columns
instance_index = df['instance_index']
avgcpu = df['avgcpu']

# Plot the graph
plt.figure(figsize=(10, 6))  # Adjust the figure size if needed
plt.scatter(instance_index, avgcpu, color='blue', marker='o', alpha=0.7)
plt.title('Avg NCU Distribution')
plt.xlabel('Instance Index')
plt.ylabel('Average NCU')
plt.grid(True)
plt.show()

