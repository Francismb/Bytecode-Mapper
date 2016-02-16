package org.mapper.utilities.data;

/**
 * Project ByteCodeMapper
 * Created by francis on 10/19/15.
 *
 * The actions which this updater can execute
 */
public enum Action {

    /**
     * Run an update
     */
    UPDATE(false, false, true),
    /**
     * Run an analysis to analyze metrics
     * of classes and fields
     */
    ANALYZE(false, true, false),
    /**
     * Download a new copy of the classes
     */
    GET(true, false, false),
    /**
     * Download a new copy of the classes and run an
     * analysis to analyze metrics of classes and fields
     */
    GET_ANALYZE(true, true, false),
    /**
     * Download a new copy of the classes and run an update
     */
    GET_UPDATE(true, false, true);

    /**
     * A flag which indicates whether to download
     * new class files or get the local ones
     */
    public boolean get;

    /**
     * A flag which indicates whether to run the
     * updater in analysis mode
     */
    public boolean analyze;

    /**
     * A flag which indicates whether to run the
     * updater in updater mode
     */
    public boolean update;

    private Action(final boolean get, final boolean analyze, final boolean update) {
        this.get = get;
        this.analyze = analyze;
        this.update = update;
    }
}

