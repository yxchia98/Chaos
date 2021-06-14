if platform?('redhat')
  execute 'Disk Loader(Linux)' do
    command <<-EOS
        cd #{ENV['HOME']}
        jdk-16.0.1/bin/java -Xmx8g -jar Javoc.jar -disk 10 50
        EOS
  end
else
  execute 'Disk Loader(Windows)' do
    command <<-EOS
        cd #{ENV['HOME']}
        .\jdk-16.0.1\bin\java -Xmx8g -jar Javoc.jar -disk 10 50
        EOS
  end
end
