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

    const file = formData.get('file'); // 'getAll'이 아닌 'get'을 사용해 단일 파일을 받음

    if (file && file.size > 0)
        updatedFormData.append('file', file);


    const res = await fetch(`${API_SERVER_HOST}/api/accounts/modify`, {
        method: 'PUT',
        body: updatedFormData
    });

    const result = await res.json();

    console.log(result);

    return {message: 'Account updated successfully', result: 'success'};
}
