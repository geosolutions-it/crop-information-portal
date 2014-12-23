package it.geosolutions.geobatch.opensdi.csvingest;

import it.geosolutions.geobatch.catalog.Identifiable;
import it.geosolutions.geobatch.flow.event.IProgressListener;

public class DummyProgressListener implements IProgressListener {

	@Override
	public void completed() {
		// TODO Auto-generated method stub

	}

	@Override
	public void failed(Throwable arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Identifiable getOwner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getProgress() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getTask() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void paused() {
		// TODO Auto-generated method stub

	}

	@Override
	public void progressing() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resumed() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setProgress(float arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTask(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void started() {
		// TODO Auto-generated method stub

	}

	@Override
	public void terminated() {
		// TODO Auto-generated method stub

	}

}
