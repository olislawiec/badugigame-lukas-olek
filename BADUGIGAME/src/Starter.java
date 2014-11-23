
public class Starter implements Runnable{
	
	Server server;
	
	Starter(Server server)
	{
		this.server=server;
	}

	public void run()
	{
		
			
		Server.Game();
		
	}
	
}
