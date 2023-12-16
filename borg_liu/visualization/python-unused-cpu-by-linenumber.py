import pandas as pd
import matplotlib.pyplot as plt

# Read CSV file into a DataFrame
file_path = 'usage-vs-request.csv'  # Replace with the actual path to your CSV file
df = pd.read_csv(file_path)

# Add a new column for line numbers
df['line_number'] = range(1, len(df) + 1)

# Extract every 1000th line
subset_df = df[df['line_number'] % 1000 == 0]

# Extract columns from the subset
line_number_subset = subset_df['line_number']
cpu_usage_subset = subset_df['cpu_usage']
cpu_request_subset = subset_df['cpu_request']

# Plot the graph
plt.figure(figsize=(10, 6))  # Adjust the figure size if needed
plt.plot(line_number_subset, cpu_usage_subset, label='CPU Usage', marker='o', linestyle='-', color='blue')
plt.plot(line_number_subset, cpu_request_subset, label='CPU Request', marker='s', linestyle='--', color='green')
plt.title('CPU Usage vs CPU Request (Every 1000th Line)')
plt.xlabel('Line Number')
plt.ylabel('CPU Resources')
plt.legend()
plt.grid(True)
plt.show()

