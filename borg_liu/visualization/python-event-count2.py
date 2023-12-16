import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt

# Load the data from the CSV file
event_data = pd.read_csv("event_type2.csv")

# Map priority level numbers to corresponding names
priority_names = {
    0: "Free Tier",
    1: "Best-effort Batch (beb) tier",
    2: "Mid-Tier",
    3: "Production Tier",
    4: "Monitoring Tier"
}

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

# Replace priority level and type numbers with corresponding names
event_data["priority_level"] = event_data["priority_level"].map(priority_names)
event_data["type"] = event_data["type"].map(event_names)

# Specify the order of the x-axis
priority_order = ["Free Tier", "Best-effort Batch (beb) tier", "Mid-Tier", "Production Tier", "Monitoring Tier"]

# Calculate the total count for each priority level
total_counts_by_priority = event_data.groupby("priority_level")["type_count"].sum()

# Calculate the percentage of the total counts for each priority level among all priority levels
percentage_by_priority = (total_counts_by_priority / event_data["type_count"].sum()) * 100

# Plot using Seaborn with specified order
plt.figure(figsize=(10, 6))
sns.barplot(x="priority_level", y="type_count", hue="type", data=event_data, order=priority_order)

# Add percentage next to each priority level on the x-axis
for i, priority_level in enumerate(priority_order):
    percentage = percentage_by_priority.loc[priority_level]
    plt.text(i, 0, f"{percentage:.2f}%", ha='center', va='bottom')

# Set labels and title
plt.xlabel("Priority Level")
plt.ylabel("Count")
plt.title("Event Type Counts by Priority Level (Percentage of Total)")

# Show the plot
plt.tight_layout()
plt.show()

