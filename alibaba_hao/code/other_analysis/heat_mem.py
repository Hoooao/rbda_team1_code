import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
import numpy as np
from matplotlib.colors import LinearSegmentedColormap

# Parameters
num_machines = 292  # Total number of machines
num_intervals = 4319   # Number of time intervals

# Initialize an empty array for the heatmap
heatmap_data = np.zeros((num_machines, num_intervals))

# Process and update heatmap data for each machine
for machine_id in range(num_machines):
    # Construct the file path. Adjust the pattern to match your file naming convention
    file_path = f'./mem/{machine_id}.txt'
    data = pd.read_csv(file_path, header=None, names=['timestamp', 'usage'], quotechar='"', sep=",")

     # Directly use the 'usage' column
    usage_data = data['usage']

    # Pad or truncate the usage data to fit num_intervals
    if len(usage_data) > num_intervals:
        usage_data = usage_data.iloc[:num_intervals]
    elif len(usage_data) < num_intervals:
        usage_data = np.pad(usage_data, (0, num_intervals - len(usage_data)), 'constant')

    # Assign the processed data to the heatmap
    heatmap_data[machine_id, :] = usage_data


# Now that we have all the data, we can create the heatmap
plt.figure(figsize=(15, 8))  # Adjust the size as needed
colors = ["blue", "white", "red"]  # Blue to white to red
n_bins = 100  # Use more bins for a smoother gradient
cmap = LinearSegmentedColormap.from_list("custom_colormap", colors, N=n_bins)
sns.heatmap(heatmap_data, cmap=cmap, cbar_kws={'label': 'used'})
plt.title('Mem utilization')
plt.xlabel('time(5 hours)')
plt.ylabel('machine id')

# Save the heatmap to a file
plt.savefig('./heatmap_mem.png', dpi=1000, bbox_inches='tight')
plt.close()

