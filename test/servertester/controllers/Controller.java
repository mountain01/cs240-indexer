package servertester.controllers;

import java.util.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import servertester.views.*;
import shared.Communicator.ClientCommunicator;
import shared.Params.*;

public class Controller implements IController {

	private IView _view;
	
	public Controller() {
		return;
	}
	
	public IView getView() {
		return _view;
	}
	
	public void setView(IView value) {
		_view = value;
	}
	
	// IController methods
	//
	
	@Override
	public void initialize() {
		getView().setHost("localhost");
		getView().setPort("39640");
		operationSelected();
	}

	@Override
	public void operationSelected() {
		ArrayList<String> paramNames = new ArrayList<String>();
		paramNames.add("User");
		paramNames.add("Password");
		
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			break;
		case GET_PROJECTS:
			break;
		case GET_SAMPLE_IMAGE:
			paramNames.add("Project");
			break;
		case DOWNLOAD_BATCH:
			paramNames.add("Project");
			break;
		case GET_FIELDS:
			paramNames.add("Project");
			break;
		case SUBMIT_BATCH:
			paramNames.add("Batch");
			paramNames.add("Record Values");
			break;
		case SEARCH:
			paramNames.add("Fields");
			paramNames.add("Search Values");
			break;
		default:
			assert false;
			break;
		}
		
		getView().setRequest("");
		getView().setResponse("");
		getView().setParameterNames(paramNames.toArray(new String[paramNames.size()]));
	}

	@Override
	public void executeOperation() {
        ClientCommunicator.getInstance().setHost(getView().getHost());
        if(!getView().getPort().matches("^[0-9]+$")){
            getView().setResponse("FAILED\n");
            return;
        }
        ClientCommunicator.getInstance().setPort(Integer.parseInt(getView().getPort()));
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			validateUser();
			break;
		case GET_PROJECTS:
			getProjects();
			break;
		case GET_SAMPLE_IMAGE:
			getSampleImage();
			break;
		case DOWNLOAD_BATCH:
			downloadBatch();
			break;
		case GET_FIELDS:
			getFields();
			break;
		case SUBMIT_BATCH:
			submitBatch();
			break;
		case SEARCH:
			search();
			break;
		default:
			assert false;
			break;
		}
	}
	
	private void validateUser() {
        String[] paramValues = getView().getParameterValues();
        ValidateUser_Params params = new ValidateUser_Params(paramValues[0],paramValues[1]);
        getView().setRequest(new XStream(new DomDriver()).toXML(params));
        getView().setResponse(ClientCommunicator.getInstance().validateUser(params).toString());
	}
	
	private void getProjects() {
        String[] paramValues = getView().getParameterValues();
        GetProjects_Params params = new GetProjects_Params(paramValues[0],paramValues[1]);
        getView().setRequest(new XStream(new DomDriver()).toXML(params));
        getView().setResponse(ClientCommunicator.getInstance().getProjects(params).toString());
	}
	
	private void getSampleImage() {
        String[] paramValues = getView().getParameterValues();
        GetSampleImage_Params params = new GetSampleImage_Params(paramValues[0],paramValues[1],Integer.parseInt(paramValues[2]));
        getView().setRequest(new XStream(new DomDriver()).toXML(params));
        getView().setResponse(ClientCommunicator.getInstance().getSampleImage(params).toString());
	}
	
	private void downloadBatch() {
        String[] paramValues = getView().getParameterValues();
        DownloadBatch_Params params = new DownloadBatch_Params(paramValues[0],paramValues[1],Integer.parseInt(paramValues[2]));
        getView().setRequest(new XStream(new DomDriver()).toXML(params));
        getView().setResponse(ClientCommunicator.getInstance().downloadBatch(params).toString());
	}
	
	private void getFields() {
        String[] paramValues = getView().getParameterValues();
        GetFields_Params params = new GetFields_Params(paramValues[0],paramValues[1],Integer.parseInt(paramValues[2]));
        getView().setRequest(new XStream(new DomDriver()).toXML(params));
        getView().setResponse(ClientCommunicator.getInstance().getFields(params).toString());
	}
	
	private void submitBatch() {
        String[] paramValues = getView().getParameterValues();
        SubmitBatch_Params params = new SubmitBatch_Params();
        params.setUsername(paramValues[0]);
        params.setPassword(paramValues[1]);
        params.setBatchId(Integer.parseInt(paramValues[2]));
        ArrayList<String[]> recordVals = new ArrayList<String[]>();
        String[] records = paramValues[3].split(";");
        for(String valueList:records){
            String[] values = valueList.split(",");
            recordVals.add(values);
        }
        params.setFieldValues(recordVals);
        getView().setRequest(new XStream(new DomDriver()).toXML(params));
        getView().setResponse(ClientCommunicator.getInstance().submitBatch(params).toString());
	}
	
	private void search() {
        String[] paramValues = getView().getParameterValues();
        String[] fields = paramValues[2].split(",");
        String[] searchVals = paramValues[3].split(",");
        Search_Params params = new Search_Params(paramValues[0],paramValues[1],searchVals,fields);
        getView().setRequest(new XStream(new DomDriver()).toXML(params));
        getView().setResponse(ClientCommunicator.getInstance().search(params).toString());
	}

}

