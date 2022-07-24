package com.code;


import java.util.concurrent.*;

public class CompletableFutureTest {
    public static void main(String[] args) throws Exception{
        // 不带返回值的执行一个线程, == new Thread().start()
        CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            println("task 1 running");
        });
        // 会阻塞等待线程执行完毕
        // 带返回值的执行一个线程 = Callable
        CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> {
            println("task 2 running");

            return "task 2 result";
        });

        /**
         * CompletableFuture 实现了CompletionStage 接口,
         * 用于定义任务在完成时的一些处理方式, 根据这些处理方式的组合可以形成任务之间的
         * 时序关系，例如 串行，并行，聚合。
         */
        // 1. 串行方式 thenApply thenAccept thenRun thenCompose 接口.
        // 通过某个已存在的future.thenXXX()串行执行, 在对应future执行完成后调用对应的函数接口.
        // 以Async 为后缀的则是异步调用, (多个任务等待一个任务执行完毕后同时并行执行的场景?)

        // 1.1 thenApple(), 接收Function函数接口 R apply(R r), 也就是说基于上一个
        // 任务的返回作为参数, 最后返回一个值.
        CompletableFuture<Void> task3 = task1.thenApply((r) -> {
            println("task3 success");
            return r;
        });

        // 1.2 thenAccept(), 接收Consumer函数接口, accept(R r), 也就是只有参数, 没有返回值
        CompletableFuture<Void> task5 = task1.thenAccept((r) -> {
            println("task5 success");
        });

        // 1.3 thenRun(), 接收Runnable接口
        CompletableFuture<Void> task6 = task1.thenRun(() -> {
            println("task6 run");
        });

        // 1.4 thenCompose(), 基于Function函数接口, 对比thenApply
        // 不同的是返回值是另一个CompletableFuture
        CompletableFuture<Void> task7 = task2.thenCompose((r) -> {
            // 需要注意此时这里的逻辑是由调用线程执行的. 通过Function.apply() 得到Future再执行
            println("task7 success");
            return CompletableFuture.runAsync(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                println("sub task7 success");
                println(r);
            });
        });

        // 2. AND 聚合 thenCombine() thenAcceptBoth() runAfterBoth();
        // 同理, 这些只要在使用的时候基于其应用的函数接口, 即可知道大概用法
        CompletableFuture<String> combineRes = CompletableFuture.supplyAsync(() -> "combine start")
                .thenCombine(
                        CompletableFuture.supplyAsync(() -> {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return "combine running";
                        }),
                        (a, b) -> {
                            println(a);
                            println(b);
                            println("combine end");
                            return "combine success";
                        }
                );
        CompletableFuture<Void> bothRes = CompletableFuture.supplyAsync(() -> "combine start")
                .thenAcceptBoth(
                        CompletableFuture.supplyAsync(() -> {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return "combine running";
                        }),
                        (a, b) -> {
                            println(a);
                            println(b);
                            println("combine end");
                        }
                );
        CompletableFuture<Void> run = CompletableFuture.supplyAsync(() -> "combine start")
                .runAfterBoth(
                        CompletableFuture.supplyAsync(() -> {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return "combine running";
                        }),
                        () -> {
                            println("combine end");
                        }
                );
        // 3. OR 聚合 applyToEither(), acceptEither(), runAfterEither() 只要其中一个获取到则满足执行
        CompletableFuture<Void> either = CompletableFuture.supplyAsync(() -> "combine start1")
                .acceptEither(
                        CompletableFuture.supplyAsync(() -> {
                            return "combine running";
                        }),
                        (a) -> {
                            println(a);
                            println("combine end");
                        }
                );
//
//        CompletableFuture<String> task3 = task1.thenCombine(task2, (a, res) -> {
//            println("task 3 running");
//
//            return "task 3 success";
//        });
//
//        println(task3.join());
//        task1.get();
//        println(task2.get());
        // 获取结果集
        task2.get();
        task1.get();
        task7.get();
    }
    public static void println(String msg) {
        System.out.println(Thread.currentThread().getName() + ": " + msg);
    }
}

