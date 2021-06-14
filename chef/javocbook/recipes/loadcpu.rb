if platform?('redhat')
  execute 'CPU Loader(Linux)' do
    command <<-EOS
	cd #{ENV['HOME']}
	jdk-16.0.1/bin/java -Xmx8g -jar Javoc.jar -cpu 10 50
	EOS
  end
else
  execute 'CPU Loader(Windows)' do
    command <<-EOS
        cd #{ENV['HOME']}
        .\jdk-16.0.1\bin\java -Xmx8g -jar Javoc.jar -cpu 10 50
        EOS
  end
end
