package com.kk.evemarket.common.events;

public class ModelProgress {
	ModelState modelState;
	Integer progress;

	public ModelProgress(ModelState state, Integer prog) {
		modelState = state;
		progress = prog;
	}

	public ModelState getModelState() {
		return modelState;
	}

	public void setModelState(ModelState modelState) {
		this.modelState = modelState;
	}

	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	@Override
	public String toString() {
		if (progress == null)
			progress = 0;
		if (modelState == null)
			modelState = ModelState.NotInitialized;
		return "State: " + modelState.name() + " - " + "Progress: " + progress.toString();
	}
}
