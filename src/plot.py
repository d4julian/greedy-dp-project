import matplotlib.pyplot as plt
from matplotlib.ticker import FuncFormatter

input_sizes = [100000, 250000, 500000, 1000000, 10000000]

program1_times = [3, 5, 11, 24, 331]
program2_times = [4, 8, 18, 28, 320]
plt.plot(input_sizes, program1_times, label='Program1', marker='o')
plt.plot(input_sizes, program2_times, label='Program2', marker='o', color='orange')

plt.xlabel('Input Size')
plt.ylabel('Execution Time (ms)')

plt.title('Execution Time vs Input Size')
plt.ticklabel_format(style='plain', axis='x')


plt.legend()
plt.grid(True, which="both", linestyle="-", linewidth=0.5)


formatter = FuncFormatter(lambda x, _: f'{int(x):,}')
plt.gca().xaxis.set_major_formatter(formatter)

plt.show()
