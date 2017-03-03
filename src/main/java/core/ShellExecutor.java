package core;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import db.Job;
import db.JobDetail;
import repository.JobDetailRepository;


public class ShellExecutor implements Runnable{

	private static String USERNAME ="nikhil.ninawe"; // username for remote host
	private static String PASSWORD ="Kusum@6112"; // password of the remote host
	private static int port=22;
	private Job job;
	private JobDetailRepository jobDetailRepository;

	/**
	 * This method will execute the script file on the server.
	 * This takes file name to be executed as an argument
	 * The result will be returned in the form of the list
	 * @param scriptFileName
	 * @return
	 */
	public ShellExecutor(Job j, JobDetailRepository jobDetailRepository) {
		this.job = j;
		this.jobDetailRepository = jobDetailRepository;
	}
	
	public void run()
	{
		StringBuilder result = new StringBuilder();
		String status = "SUCCESS";
		try{
			/**
			 * Create a new Jsch object
			 * This object will execute shell commands or scripts on server
			 */
			JSch jsch = new JSch();

			/*
			 * Open a new session, with your username, host and port
			 * Set the password and call connect.
			 * session.connect() opens a new connection to remote SSH server.
			 * Once the connection is established, you can initiate a new channel.
			 * this channel is needed to connect to remotely execution program
			 */
			Session session = jsch.getSession(USERNAME, this.job.getMachineIpAddress(), port);
			session.setConfig("StrictHostKeyChecking", "no");
			session.setPassword(PASSWORD);
			session.connect();

			//create the excution channel over the session
			ChannelExec channelExec = (ChannelExec)session.openChannel("exec");

			// Gets an InputStream for this channel. All data arriving in as messages from the remote side can be read from this stream.
			InputStream in = channelExec.getInputStream();

			// Set the command that you want to execute
			// In our case its the remote shell script
			channelExec.setCommand("python "+ this.job.getFileName());

			// Execute the command
			channelExec.connect();

			// Read the output from the input stream we set above
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line;

			//Read each line from the buffered reader and add it to result list
			// You can also simple print the result here 
			while ((line = reader.readLine()) != null)
			{
				result.append(line);
			}

			//retrieve the exit status of the remote command corresponding to this channel
			int exitStatus = channelExec.getExitStatus();

			//Safely disconnect channel and disconnect session. If not done then it may cause resource leak
			channelExec.disconnect();
			session.disconnect();
		}
		catch(Exception e){
			status = "FAILURE";
			result.append(e);
		}
		String result1 = result.toString();
		JobDetail jobDetail = new JobDetail();
		jobDetail.setJobId(job.getId());
		jobDetail.setLastRunTime(Calendar.getInstance().getTime());
		jobDetail.setResult(result1);
		jobDetail.setStatus(status);
		jobDetailRepository.save(jobDetail);
	}
}
