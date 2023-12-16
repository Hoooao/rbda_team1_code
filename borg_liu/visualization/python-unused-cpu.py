import pandas as pd
import matplotlib.pyplot as plt

# Read CSV file into a DataFrame
file_path = 'usage-vs-request.csv'  # Replace with the actual path to your CSV file
df = pd.read_csv(file_path)

# Plot the graph
plt.figure(figsize=(10, 6))  # Adjust the figure size if needed
plt.plot(df['unused'], df['cpu_usage'], label='CPU Usage', marker='o', linestyle='-', color='blue')
plt.plot(df['unused'], df['cpu_request'], label='CPU Request', marker='s', linestyle='--', color='green')
plt.title('CPU Usage vs CPU Request')
plt.xlabel('Unused')
plt.ylabel('CPU Resources')
plt.legend()
plt.grid(True)
plt.show()

