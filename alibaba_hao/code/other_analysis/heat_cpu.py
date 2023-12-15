import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
import numpy as np

# Parameters
num_machines = 10  # Total number of machines
num_intervals = 4319   # Number of time intervals

# Initialize an empty array for the heatmap
heatmap_data = np.zeros((num_machines, num_intervals))

# Process and update heatmap data for each machine
for machine_id in range(num_machines):
    # Construct the file path. Adjust the pattern to match your file naming convention
    file_path = f'./cpu/{machine_id+1}.txt'
    
    # Read the data into memory. Adjust the reading function according to your file format
    data = pd.read_csv(file_path, header=None, names=['timestamp', 'usage'], quotechar='"', sep=",")
    

    # Update the corresponding row in the heatmap data
    heatmap_data[machine_id, :] = data

# Now that we have all the data, we can create the heatmap
plt.figure(figsize=(15, 8))  # Adjust the size as needed
sns.heatmap(heatmap_data, cmap='viridis', cbar_kws={'label': 'used'})
plt.title('CPU utilization')
plt.xlabel('time (hour)')
plt.ylabel('machine id')

# Save the heatmap to a file
plt.savefig('/mnt/data/heatmap_large_dataset.png', dpi=300, bbox_inches='tight')
plt.close()

