import pandas as pd
import matplotlib.pyplot as plt

# Read CSV file into a DataFrame
file_path = 'usage-vs-request-sampled.csv'  # Replace with the actual path to your CSV file
df = pd.read_csv(file_path)

# Plot the graph with data points
plt.figure(figsize=(10, 6))  # Adjust the figure size if needed
plt.scatter(df['cpu_request'], df['cpu_usage'], color='blue', marker='o')
plt.title('CPU Usage vs CPU Request')
plt.xlabel('CPU Request (NCU)')
plt.ylabel('CPU Usage (NCU)')
plt.grid(True)
plt.show()

