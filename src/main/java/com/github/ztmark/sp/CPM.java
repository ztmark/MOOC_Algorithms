package com.github.ztmark.sp;

import java.util.List;

// 关键路径
// 解决 任务和任务之间有前后关系的限制，同时可以有无限多任务同时进行的情况下
// 如何安排任务，使所有任务能在最短时间内完成
// 这类问题可以转换成求最长路径问题
public class CPM {


    public CPM(Task[] tasks) {
        int taskNum = tasks.length;
        // 每个 task 是一条边，涉及到两个顶点，然后有一个开始顶点和一个结束顶点
        // 任务和任务之的关系由一条权重为0的有向边来表示

        int s = 2 * taskNum; // 开始顶点
        int e = 2 * taskNum + 1; // 结束顶点

        EdgeWeightedDigraph digraph = new EdgeWeightedDigraph(taskNum * 2 + 2);
        for (Task task : tasks) {
            digraph.addEdge(new DirectedEdge(task.num, task.num+taskNum, task.time));
            if (task.successor != null && !task.successor.isEmpty()) {
                for (Integer t : task.successor) {
                    // 添加一条 task.num 到 t 的权重为 0 的边
                    digraph.addEdge(new DirectedEdge(task.num, t, 0));
                }
            }
            // 开始顶点到所有的其他顶点的边
            digraph.addEdge(new DirectedEdge(s, task.num, 0));
            // 其他所有顶点到结束顶点的边
            digraph.addEdge(new DirectedEdge(task.num+taskNum, e, 0));
        }
        AcyclicLP lp = new AcyclicLP(digraph, s);
        System.out.println("Start print each task's start time");
        for (int i = 0; i < taskNum; i++) {
            System.out.println("taskNum: " + i + " start time: " + lp.distTo(i));
        }
        System.out.println("finished time: " + lp.distTo(e));
    }

    private static class Task {
        int num;
        double time;
        List<Integer> successor; // 当前任务必须在这些任务之前完成

        public Task(int num, double time, List<Integer> successor) {
            this.num = num;
            this.time = time;
            this.successor = successor;
        }
    }
}
