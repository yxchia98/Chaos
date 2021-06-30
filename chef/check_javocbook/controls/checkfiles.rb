control 'checkjavocfiles' do
  impact 1.0
  title 'Check Javocbook files'
  desc 'Check for Javocbook files and directories on target machine.'
  describe directory('jdk-16.0.1') do
    it { should exist }
  end
  describe file('Javoc.jar') do
    it { should exist }
  end
end
