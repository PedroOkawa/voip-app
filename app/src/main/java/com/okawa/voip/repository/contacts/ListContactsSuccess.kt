package com.okawa.voip.repository.contacts

import com.okawa.voip.model.Contact
import com.okawa.voip.repository.status.OnSuccess

class ListContactsSuccess(contacts: List<Contact>) : OnSuccess<List<Contact>>(contacts)