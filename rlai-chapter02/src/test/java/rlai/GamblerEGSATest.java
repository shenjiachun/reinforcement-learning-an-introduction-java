package rlai;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import javafx.application.Application;
import javafx.stage.Stage;
import rlai.reward.ExponentialRecencyWeightedAverageUpdater;
import rlai.strategy.BanditStrategy;
import rlai.strategy.EpsilonGreedyStrategy;

public class GamblerEGSATest extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        List<Bandit> bandits = new ArrayList<>();
        bandits.add(new GaussianBandit(20));
        bandits.add(new GaussianBandit(15));
        bandits.add(new GaussianBandit(10));
        bandits.add(new GaussianBandit(5));
        bandits.add(new GaussianBandit(0));
        bandits.add(new GaussianBandit(-5));
        bandits.add(new GaussianBandit(-10));
        bandits.add(new GaussianBandit(-15));
        bandits.add(new GaussianBandit(-20));
        bandits.add(new GaussianBandit(-25));
        bandits.add(new GaussianBandit(-30));
        bandits.add(new GaussianBandit(-100));
        bandits.add(new GaussianBandit(40));
        bandits.add(new GaussianBandit(60));
        bandits.add(new GaussianBandit(90));
        bandits.add(new GaussianBandit(100));

        int iterateTimes = 10000;
        RLAIChart2D chart = new RLAIChart2D("Epsilon-Greedy", "Iteration", "Average Reward");

        DoubleStream.of(0.01, 0.03, 0.06, 0.07, 0.08, 0.1).forEach(d -> {
            BanditStrategy strategy = new EpsilonGreedyStrategy(d, bandits.size(),
                new ExponentialRecencyWeightedAverageUpdater(0.8));
            Gambler gambler = new Gambler(bandits, strategy);
            double[][] data = new double[2][iterateTimes];
            data[0] = IntStream.range(0, iterateTimes).asDoubleStream().toArray();
            data[1] = IntStream.range(0, iterateTimes).mapToDouble(i -> gambler.play()).toArray();
            chart.addLine("ε=" + String.valueOf(d), data);
        });

        primaryStage.setScene(chart.toScene());
        primaryStage.setTitle("Epsilon-Greedy");
        primaryStage.setWidth(1366);
        primaryStage.setHeight(768);
        primaryStage.show();
    }
}