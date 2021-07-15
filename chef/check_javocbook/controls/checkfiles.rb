control 'checkjavocfiles' do
  impact 1.0
  title 'Check Javocbook files'
  desc 'Check for Javocbook files and directories on target machine.'
  describe file('Javoc.jar') do
    it { should exist }
  end
end
