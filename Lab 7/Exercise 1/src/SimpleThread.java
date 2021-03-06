public class SimpleThread extends Thread{

	Resource resource;
	
	public void run() {
		for(int i = 0; i<50; i++){
			try {
			System.out.println(resource.increment());
			Thread.sleep(10); // had to add this to create a race condition
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	SimpleThread(Resource resource){
		this.resource = resource;
	}

	public static void main(String args[]) {
		Resource resource = new Resource();
		Thread t = new SimpleThread(resource);
		Thread s = new SimpleThread(resource);
		
		t.start();
		s.start();
	}
}