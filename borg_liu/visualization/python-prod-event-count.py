import pandas as pd
import matplotlib.pyplot as plt

# Load the data from the CSV file
event_data = pd.read_csv("prod_event_type.csv")

# Map type numbers to corresponding names
event_names = {
    0: "SUBMIT",
    1: "QUEUE",
    2: "ENABLE",
    3: "SCHEDULE",
    4: "EVICT",
    5: "FAIL",
    6: "FINISH",
    7: "KILL",
    8: "LOST",
    9: "UPDATE_PENDING",
    10: "UPDATE_RUNNING"
}

# Replace type numbers with corresponding names
event_data["type"] = event_data["type"].map(event_names)

# Calculate the total count
total_count = event_data["type_count"].sum()

# Plot the bar chart
plt.bar(event_data["type"], event_data["type_count"])

# Set labels and title
plt.xlabel("Event Type")
plt.ylabel("Count")
plt.title("Event Type Counts (Prod)")

# Rotate x-axis labels for better readability
plt.xticks(rotation=45, ha="right")

# Add percentage on top of each bar
for i, count in enumerate(event_data["type_count"]):
    percentage = (count / total_count) * 100
    plt.text(i, count, f"{percentage:.2f}%", ha='center', va='bottom')

# Show the plot
plt.tight_layout()
plt.show()

