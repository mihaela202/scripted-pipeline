node {
    stage('Pull Repo') {
        git url: 'https://github.com/ikambarov/packer.git'
    }

    stage('Packer Validate'){
        sh 'packer validate apache.json'
    }
    
    withCredentials([usernamePassword(credentialsId: 'jenkins-aws-access-key', passwordVariable: 'AWS_SECRET_ACCESS_KEY', usernameVariable: 'AWS_ACCESS_KEY_ID')])
        withEnv([ 'AWS_REGION=us-east-1' ])

}