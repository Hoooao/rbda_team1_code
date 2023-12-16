import pandas as pd
import matplotlib.pyplot as plt
from datetime import datetime, timedelta

# Load the data from the CSV file
data = pd.read_csv("7-day-resource-requests.csv")

# Extract the columns
hour_offset = data["hour_offset"]
total_cpu_request = data["total_cpu_request"]

# Calculate datetime based on start time
start_time = datetime(2019, 5, 1)

# Create a new column with descriptive labels
data["datetime"] = start_time + pd.to_timedelta(hour_offset, unit='h')

# Plot the graph
plt.plot(data["datetime"], total_cpu_request, marker='o', linestyle='-')

# Set labels and title
plt.xlabel("Date and Time")
plt.ylabel("Total CPU Requests")
plt.title("7-Day CPU Requests")

# Format x-axis labels for better readability
plt.xticks(rotation=45, ha="right")

# Show the plot
plt.tight_layout()
plt.show()

