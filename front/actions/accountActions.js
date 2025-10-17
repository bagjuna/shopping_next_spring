'use client'


const API_SERVER_HOST = process.env.API_SERVER_HOST || 'http://localhost:8080';

export const createAccount = async (prevState, formData) => {

    const res = await fetch(`${API_SERVER_HOST}/api/accounts/register`, {
        method: 'POST',
        body: formData,
    });

    const result = await res.json();

    console.log(result);

    return {message: 'Account updated successfully', result: 'success'};

}

export const putAccount = async (prevState, formData) => {



    const updatedFormData = new FormData();
    updatedFormData.append('email', formData.get('email'));
    updatedFormData.append('nickname', formData.get('nickname'));

    const fileNames = formData.getAll('fileNames')

    console.log("fileNames length: " + fileNames)
    if(fileNames.length > 0){
        fileNames.forEach(fileName => {
            updatedFormData.append('fileNames', fileName);
        })
    }
    // Append all files to the new FormData object
    const files = formData.getAll('files');
    if(files.length > 0) {
        files.forEach(file => {
            console.log("----------------------------------", file)
            if (file && file.size > 0) {
                updatedFormData.append('files', file);
            }
        });
    }


    const res = await fetch(`${API_SERVER_HOST}/api/accounts/modify`, {
        method: 'PUT',
        body: updatedFormData
    });

    const result = await res.json();

    console.log(result);

    return {message: 'Account updated successfully', result: 'success'};
}
