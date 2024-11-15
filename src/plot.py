import matplotlib.pyplot as plt
from matplotlib.ticker import FuncFormatter

input_sizes = [1000, 2500, 5000, 7500, 10000]

program1_times = [0, 1, 1, 1, 1]
program2_times = [1, 0, 1, 1, 2]
program3_times = [64978, 67199, 56156, 52025, 65666]
program4_times = [5, 18, 165, 286, 342]
program5a_times = [0, 0, 1, 1, 1]
program5b_times = [1, 0, 1, 1, 1]

#plt.plot(input_sizes, program1_times, label='Program1', marker='o', color='blue')
#plt.plot(input_sizes, program2_times, label='Program2', marker='o', color='orange')
#plt.plot(input_sizes, program3_times, label='Program3', marker='o', color='green')
#plt.plot(input_sizes, program4_times, label='Program4', marker='o', color='red')
plt.plot(input_sizes, program5a_times, label='Program5A', marker='o', color='purple')
plt.plot(input_sizes, program5b_times, label='Program5B', marker='o', color='pink')



plt.xlabel('Input Size')
plt.ylabel('Execution Time (ms)')

plt.title('Execution Time vs Input Size')
plt.ticklabel_format(style='plain', axis='x')


plt.legend()
plt.grid(True, which="both", linestyle="-", linewidth=0.5)


formatter = FuncFormatter(lambda x, _: f'{int(x):,}')
plt.gca().xaxis.set_major_formatter(formatter)

plt.show()
