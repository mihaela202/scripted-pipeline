node {
  stage("Clone Repo"){
    git credentialsId: 'jenkins-master-ssh-key', url: 'https://github.com/ikambarov/simple-site.git'
  }
  stage("Testing"){
    echo 'Testing..'
  }
  stage("Deploying "){
    echo 'Deploying....'
  }
  stage("Send Notification to Slack"){
    emailext body: '', subject: 'jenkis build status ', to: 'varanita.mihaela@gmail.com'
  }
}