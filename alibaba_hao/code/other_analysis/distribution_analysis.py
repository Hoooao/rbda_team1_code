import pandas as pd
import matplotlib.pyplot as plt
import matplotlib.ticker as ticker

# Load data from a text file
# Replace 'data.txt' with your actual file path
df = pd.read_csv('data.txt', delim_whitespace=True, header=None, names=['usage', 'num_instances'])

# Sort the DataFrame by 'usage' to have a nice ordered bar chart
df.sort_values('usage', inplace=True)

# Set the figure size to make sure we have enough space for all bars
plt.figure(figsize=(20, 10))  # You might need to adjust this depending on the number of unique usage values

# Create a bar plot
# Set the width of each bar to a small fraction, depending on the number of bars
bar_width = 0.003  # This should be adjusted based on your actual data
plt.bar(df['usage'], df['num_instances'], width=bar_width)

# Set labels and title
plt.xlabel('One Hour CPU Usage')
plt.ylabel('Number of Instances')
plt.title('Distribution of Usage')

# Optional: Rotate the x-axis labels for better visibility if they overlap
plt.xticks(rotation=90)

# Save the plot to a file
plt.savefig('distribution_plot.png', bbox_inches='tight')  # bbox_inches='tight' saves the figure with a tight layout

# Show the plot
plt.show()
