properties([
    parameters([
        string(defaultValue: '', description: 'Please enter VM IP', name: 'nodeIP', trim: true)
        ])
    ])

if (nodeIP?.trim()) {

    node {
        stage('Pull Repo') {
            git url: 'https://github.com/mihaela202/packer.git'
        }
        withCredentials([usernamePassword(credentialsId: 'jenkins-aws-access-key', passwordVariable: 'AWS_SECRET_ACCESS_KEY', usernameVariable: 'AWS_ACCESS_KEY_ID')]) {
            withEnv([ 'AWS_REGION=us-east-1', "PACKER_AMI_NAME=apache" ]) {
                stage('Packer Validate') {
                    sh 'packer validate init.json'
                  }
                stage('Packer Build') {
                    sh 'packer build init.json'
                }
            }
        }
    }
}
else {
    error 'Please enter valid IP address'
}
