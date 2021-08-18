document.addEventListener('DOMContentLoaded', function () {
    const contactWrapperDOM = document.querySelector("#contact-wrapper");
    const contactTemplateDOM = document.querySelector("#contact-template");

    const contactFormDOM = document.querySelector("#contact-form");

    const contactRenderer = new ContactRenderer(contactWrapperDOM, contactTemplateDOM);

    const contactService = new ContactService(contactRenderer);

    const contactListener = new ContactListener(contactFormDOM,contactService);

    //const contactRenderer = new ContactRenderer(contactWrapperDOM, contactTemplateDOM);
    //const contactService = new ContactService(contactRenderer);

    contactFormDOM.addEventListener('click', contactListener);
    //TODO **написать слушатель и обработчик событий кнопок контакта.

});

class ContactListener {

    constructor(contactFormDOM,contactService) {
        this.contactFormDOM = contactFormDOM;
        this.contatService = contactService;
    }

    handleEvent(event) {
        console.log("test");
        const aElementDom = event.target.closest("a");

        if (aElementDom !== null) {
            // if (aElementDom.dataset.action === "add")
            //     this.add();
            //     this["add"]();
            // else if (aElementDom.dataset.action === "save")
            //     this.save();
            //     this["save"]();
            // else if (aElementDom.dataset.action === "cancel")
            //     this.cancel();
            //     this["cancel"]();
            const action = aElementDom.dataset.action;
            this[action](event);
        }
    }

    add(event) {
        console.log("add");
        const contactForm = event.currentTarget;
        const contact = {
            firstName: contactForm.elements.firstName.value,
            lastName: contactForm.elements.lastName.value,
            age: contactForm.elements.age.value,
        }

        console.log(contact);
        //TODO добавить обращение к сервису для сохранения контакта
        this.contatService.add(contact);


    }

    save(event) {
        console.log("edit/save")
        // TODO

    }

    cancel(event) {
        console.log("cancel")
    }
}

class ContactRenderer {

    constructor(contactWrapperDOM, contactTemplateDOM) {
        this.contactWrapperDOM = contactWrapperDOM;
        this.contactTemplateDOM = contactTemplateDOM;
    }

    _renderContact(contact) {
        const newNode = this.contactTemplateDOM.cloneNode(true);

        newNode.querySelector('span[data-id="name"]').innerHTML = contact.firstName;
        newNode.querySelector('span[data-id="lastname"]').innerHTML = contact.lastName;

        newNode.classList.toggle("hide-element");//изменить на противоположное
        // newNode.setAttribute("id", 'contact-' + contact.id);
        newNode.setAttribute("id", `contact-${contact.id}`);

        this.contactWrapperDOM.append(newNode);
    }

    renderContacts(contacts = this.fakeContacts) {//значение по умолчанию
        for (const contact of contacts) {
            this._renderContact(contact);
        }
    }
}

class ContactService {

    fakeContacts = [
        {firstName: "Max", lastName: "Mustermann", age: 25, id: 1},
        {firstName: "Vasja", lastName: "Pupkin", age: 18, id: 2},
        {firstName: "John", lastName: "Doe", age: 35, id: 3},
        {firstName: "Mark", lastName: "Schmidt", age: 43, id: 4},
        {firstName: "Anna", lastName: "Baumann", age: 34, id: 5}
    ];

    constructor(contactRenderer) {
        this.contactRenderer = contactRenderer;

        this.getAll();
    }

    getAll() {
        this.contactRenderer.renderContacts(this.fakeContacts);
    }

    remove(contact) {
        //TODO
        //this.remove(contact)
    }

    add(contact) {
        //TODO
        this.fakeContacts.push(contact);
        this.contactRenderer.renderContacts([contact]);
    }

    edit(contact) {
        //TODO
        //this.edit(contact)

    }
}