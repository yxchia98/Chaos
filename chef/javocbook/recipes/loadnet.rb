if platform?('redhat')
  execute 'Network Loader(Linux)' do
    command <<-EOS
        cd #{ENV['HOME']}
        jdk-16.0.1/bin/java -Xmx8g -jar Javoc.jar -net 10 50
        EOS
  end
else
  execute 'Network Loader(Windows)' do
    command <<-EOS
        cd #{ENV['HOME']}
        .\jdk-16.0.1\bin\java -Xmx8g -jar Javoc.jar -net 10 50
        EOS
  end
end
