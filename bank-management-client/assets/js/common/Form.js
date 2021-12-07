const Form = {

    getValueInput: idInput => {
        const input = $(idInput)
        const type = input.attr('type')
        switch (type) {
            case 'text':
            case 'password':
                return input.val()

            case 'checkbox':
                return input.is(':checked')
        }
    },

    getData: idForm => {
        const data = {}
        $(`${idForm} input`).each(function () {
            const input = $(this)
            data[`${input.attr('name')}`] = Form.getValueInput(`#${input.attr('id')}`)
        })
        return data
    }
}