properties([
    parameters([
        string(defaultValue: '', description: 'Please enter VM IP', name: 'nodeIP', trim: true)
        ])
    ])

node {
    
    stage ('Pull the repo'){
        git  url: 'https://github.com/ikambarov/spring-petclinic.git'
    }
    withCredentials([sshUserPrivateKey(credentialsId: 'jenkins-master-ssh-key', keyFileVariable: 'SSHKEY', passphraseVariable: '', usernameVariable: 'SSHUSERNAME')])
        stage ("Install epel-release"){
            sh 'ssh  -o StrictHostKeyChecking=no -i $SSHKEY $SSHUSERNAME@${nodeIP} yum install epel-release -y'
        }
        stage ( "install git ") {
            sh 'ssh  -o StrictHostKeyChecking=no -i $SSHKEY $SSHUSERNAME@${nodeIP} yum install git -y'
        }     
        stage ( "install ansible ")  {
            sh 'ssh  -o StrictHostKeyChecking=no -i $SSHKEY $SSHUSERNAME@${nodeIP} yum install ansible -y'
        }

    withEnv(['ANSIBLE_HOST_KEY_CHECKING=False' ]) {
            stage("Install Prerequisites"){
                ansiblePlaybook credentialsId: 'jenkins-master-ssh-key', inventory: '${nodeIP},', playbook: 'docker-compose.yml'
                }
         }
}