if platform?('redhat')
  execute 'Memory Loader(Linux)' do
    command <<-EOS
        cd #{ENV['HOME']}
        jdk-16.0.1/bin/java -Xmx8g -jar Javoc.jar -mem 10 50
        EOS
  end
else
  execute 'Memory Loader(Windows)' do
    command <<-EOS
        cd #{ENV['HOME']}
        .\jdk-16.0.1\bin\java -Xmx8g -jar Javoc.jar -mem 10 50
        EOS
  end
end
