import pandas as pd
import matplotlib.pyplot as plt

# Load the data from the CSV file
df = pd.read_csv('usage_by_priority.csv')

# Calculate the total CPU usage percentage
total_cpu = df['total_cpu_usage'].sum()
df['cpu_percentage'] = (df['total_cpu_usage'] / total_cpu) * 100

# Plot the bar chart
fig, ax = plt.subplots()
bars = ax.bar(df['priority_level'], df['total_cpu_usage'], color='blue')

# Add percentage labels on top of each bar
for bar, percentage in zip(bars, df['cpu_percentage']):
    height = bar.get_height()
    ax.text(bar.get_x() + bar.get_width() / 2, height, f'{percentage:.2f}%', ha='center', va='bottom')

# Set labels and title
ax.set_xlabel('Priority Level')
ax.set_ylabel('Total CPU Usage (NCU)')
ax.set_title('CPU Usage (Jobs) by Priority Level')

# Show the plot
plt.show()

