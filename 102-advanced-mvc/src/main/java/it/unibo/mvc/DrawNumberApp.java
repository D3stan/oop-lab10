package it.unibo.mvc;

import java.io.FileNotFoundException;
import java.lang.module.ModuleDescriptor.Builder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 */
public final class DrawNumberApp implements DrawNumberViewObserver {
    private static final String configFile = "config.yml";

    private final DrawNumber model;
    private final List<DrawNumberView> views;

    /**
     * @param views
     *            the views to attach
     */
    public DrawNumberApp(final DrawNumberView... views) {
        /*
         * Side-effect proof
         */
        this.views = Arrays.asList(Arrays.copyOf(views, views.length));
        for (final DrawNumberView view: views) {
            view.setObserver(this);
            view.start();
        }
        final Map<String, Integer> configMap = DrawNumberConfig.retrieveConfiguration(configFile);
        final Configuration.Builder configBuilder = new Configuration.Builder();
        configBuilder.setAttempts(configMap.get("attempts"));
        configBuilder.setMax(configMap.get("maximum"));
        configBuilder.setMin(configMap.get("minimum"));
        Configuration config = configBuilder.build();

        if (!config.isConsistent()) {
            config = new Configuration.Builder().build();
            for (final DrawNumberView view: views) {
                view.displayError("Incosistent Config");
            }
        }

        this.model = new DrawNumberImpl(config.getMin(), config.getMax(), config.getAttempts());
    }

    @Override
    public void newAttempt(final int n) {
        try {
            final DrawResult result = model.attempt(n);
            for (final DrawNumberView view: views) {
                view.result(result);
            }
        } catch (IllegalArgumentException e) {
            for (final DrawNumberView view: views) {
                view.numberIncorrect();
            }
        }
    }

    @Override
    public void resetGame() {
        this.model.reset();
    }

    @Override
    public void quit() {
        /*
         * A bit harsh. A good application should configure the graphics to exit by
         * natural termination when closing is hit. To do things more cleanly, attention
         * should be paid to alive threads, as the application would continue to persist
         * until the last thread terminates.
         */
        System.exit(0);
    }

    /**
     * @param args
     *            ignored
     * @throws FileNotFoundException 
     */
    public static void main(final String... args) throws FileNotFoundException {
        new DrawNumberApp(new DrawNumberViewImpl(), new DrawNumberViewImpl(), new PrintStreamView(System.out), new PrintStreamView("log.txt"));
    }

}
