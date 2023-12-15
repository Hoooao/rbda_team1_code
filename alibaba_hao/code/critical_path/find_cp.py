import pandas as pd
import networkx as nx
import matplotlib.pyplot as plt


data = pd.read_csv('data.csv')

trace_id = 'T_19364532164'
filtered_data = data[data['traceid'] == trace_id]

G = nx.DiGraph()

for _, row in filtered_data.iterrows():
    G.add_edge(row['um'], row['dm'], weight=row['rt'])

length, path = nx.single_source_dijkstra(G, source='USER', weight='weight')

# Print or process the critical path
print("Critical Path:", path)
print("Total Response Time:", length)


plt.figure(figsize=(6, 4)) 
pos = nx.circular_layout(G)
nx.draw(G, pos, with_labels=True, node_color='skyblue', edge_color='gray')
edge_labels = nx.get_edge_attributes(G, 'weight')
nx.draw_networkx_edge_labels(G, pos, edge_labels=edge_labels)
plt.title("Call Graph Visualization")
plt.savefig('./graph.png', dpi=300, bbox_inches='tight')






