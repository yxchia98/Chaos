package org.javocmaven.Javocmaven;

import java.util.concurrent.TimeUnit;

import com.martensigwart.fakeload.FakeLoad;
import com.martensigwart.fakeload.FakeLoadExecutor;
import com.martensigwart.fakeload.FakeLoadExecutors;
import com.martensigwart.fakeload.FakeLoads;
import com.martensigwart.fakeload.MemoryUnit;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	FakeLoad fakeload = FakeLoads.create()
    		    .lasting(10, TimeUnit.SECONDS)
    		    .withCpu(50);
    	FakeLoadExecutor executor = FakeLoadExecutors.newDefaultExecutor();
    	executor.execute(fakeload);
    }
}
