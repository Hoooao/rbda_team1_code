import pandas as pd
import matplotlib.pyplot as plt

# Load the data from the CSV file
event_data = pd.read_csv("event_type.csv")

# Map event type numbers to corresponding names
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

# Replace event type numbers with corresponding names
event_data["type"] = event_data["type"].map(event_names)

# Calculate the total count within each priority level
event_data['total_count'] = event_data.groupby('priority_level')['type_count'].transform('sum')

# Plot the grouped bar chart
fig, ax = plt.subplots()
bar_width = 0.2
bar_positions = []

for i, priority_level in enumerate(event_data["priority_level"].unique()):
    subset = event_data[event_data["priority_level"] == priority_level]
    percentages = subset["type_count"] / subset["total_count"] * 100
    positions = [pos + bar_width * i for pos in range(len(subset))]
    bar_positions.extend(positions)

    colors = plt.cm.viridis(i / len(event_data["priority_level"].unique()))

    # Plot the bars
    ax.bar(
        positions,
        percentages,
        width=bar_width,
        label=priority_level,
        color=colors,
        align='center'
    )

# Set labels and title
plt.xlabel("Event Type")
plt.ylabel("Percentage within Priority Level")
plt.title("Event Type Percentages by Priority Level")

# Set x-axis labels and ticks
ax.set_xticks([pos + bar_width * (len(event_data["priority_level"].unique()) - 1) / 2 for pos in range(len(bar_positions)//len(event_data["priority_level"].unique()))])
ax.set_xticklabels(event_data["type"].unique(), rotation=45, ha="right")

# Display a legend
plt.legend(title="Priority Level", bbox_to_anchor=(1.05, 1), loc='upper left')

# Show the plot
plt.tight_layout()
plt.show()

