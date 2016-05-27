package com.github.pineasaurusrex.inference_engine;

public class TruthTableAlgorithmResult implements SearchAlgorithmResult {
    private int models;

    public TruthTableAlgorithmResult(int models) {
        this.models = models;
    }

    public int getModels() {
        return models;
    }

    @Override
    public String toString() {
        return String.valueOf(models);
    }
}
